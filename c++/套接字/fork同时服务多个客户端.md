#### fork同时服务多个客户端

如何同时服务多个客户端呢？在未讲到select/poll/epoll等高级IO之前，比较老土的办法是使用fork来实现。网络服务器通常用fork来同时服务多个客户端，父进程专门负责监听端口，每次accept一个新的客户端连接就fork出一个子进程专门服务这个客户端。但是子进程退出时会产生僵尸进程，父进程要注意处理SIGCHLD信号和调用wait清理僵尸进程，最简单的办法就是直接忽略SIGCHLD信号。 

```c
/*************************************************************************
    > File Name: echoser.c
    > Author: Simba
    > Mail: dameng34@163.com
    > Created Time: Fri 01 Mar 2013 06:15:27 PM CST
 ************************************************************************/

#include<stdio.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<stdlib.h>
#include<errno.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#include<string.h>
#include<signal.h>

#define ERR_EXIT(m) \
    do { \
        perror(m); \
        exit(EXIT_FAILURE); \
    } while (0)

void do_service(int);

int main(void)
{
    signal(SIGCHLD, SIG_IGN);
    int listenfd; //被动套接字(文件描述符），即只可以accept, 监听套接字
    if ((listenfd = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
        //  listenfd = socket(AF_INET, SOCK_STREAM, 0)
        ERR_EXIT("socket error");

    struct sockaddr_in servaddr;
    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(5188);
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
    /* servaddr.sin_addr.s_addr = inet_addr("127.0.0.1"); */
    /* inet_aton("127.0.0.1", &servaddr.sin_addr); */

    int on = 1;
    if (setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0)
        ERR_EXIT("setsockopt error");

    if (bind(listenfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0)
        ERR_EXIT("bind error");

    if (listen(listenfd, SOMAXCONN) < 0) //listen应在socket和bind之后，而在accept之前
        ERR_EXIT("listen error");

    struct sockaddr_in peeraddr; //传出参数
    socklen_t peerlen = sizeof(peeraddr); //传入传出参数，必须有初始值
    int conn; // 已连接套接字(变为主动套接字，即可以主动connect)

    pid_t pid;

    while (1)
    {
        if ((conn = accept(listenfd, (struct sockaddr *)&peeraddr, &peerlen)) < 0) //3次握手完成的序列
            ERR_EXIT("accept error");
        printf("recv connect ip=%s port=%d\n", inet_ntoa(peeraddr.sin_addr),
               ntohs(peeraddr.sin_port));

        pid = fork();
        if (pid == -1)
            ERR_EXIT("fork error");
        if (pid == 0)
        {
            // 子进程
            close(listenfd);
            do_service(conn);
            exit(EXIT_SUCCESS);
        }
        else
            close(conn); //父进程
    }

    return 0;
}

void do_service(int conn)
{
    char recvbuf[1024];
    while (1)
    {
        memset(recvbuf, 0, sizeof(recvbuf));
        int ret = read(conn, recvbuf, sizeof(recvbuf));
        if (ret == 0)   //客户端关闭了
        {
            printf("client close\n");
            break;
        }
        else if (ret == -1)
            ERR_EXIT("read error");
        fputs(recvbuf, stdout);
        write(conn, recvbuf, ret);
    }
}
```



上述程序利用了一点，就是父子进程共享打开的文件描述符，因为在子进程已经用不到监听描述符，故将其关闭，而连接描述符对父进程也没价值，将其关闭。当某个客户端关闭，则read 返回0，退出循环，子进程顺便exit，但如果没有设置对SIGCHLD信号的忽略，则因为父进程还没退出，故子进程会变成僵尸进程。

现在先运行server，再打开另外两个终端，运行client（直接用[回射客户/服务器程序](http://blog.csdn.net/simba888888/article/details/9025581)中的客户端程序），可以看到server输出如下：

simba@ubuntu:~/Documents/code/linux_programming/UNP/socket$ ./echoser_fork 
recv connect ip=127.0.0.1 port=46452
recv connect ip=127.0.0.1 port=46453



在另一个终端ps一下：

simba@ubuntu:~$ ps aux | grep echoser
simba   3300  0.0  0.0  2008  280 pts/0   S+  22:10  0:00 ./echoser_fork
simba   3303  0.0  0.0  2008   60 pts/0   S+  22:10  0:00 ./echoser_fork
simba   3305  0.0  0.0  2008   60 pts/0   S+  22:10  0:00 ./echoser_fork
simba   3313  0.0  0.0  4392  836 pts/3   S+  22:12  0:00 grep --color=auto echoser
simba@ubuntu:~$ 

发现共有3个进程，其中一个是父进程处于监听中，另外两个是子进程处于对客户端服务中，现在ctrl+c 掉其中一个client，由上面的分析可知对应服务的子进程也会退出，而因为我们设置了父进程对SIGCHLD信号进行忽略，故不会产生僵尸进程，输出如下：

simba@ubuntu:~$ ps aux | grep echoser
simba   3300  0.0  0.0  2008  280 pts/0   S+  22:10  0:00 ./echoser_fork
simba   3305  0.0  0.0  2008   60 pts/0   S+  22:10  0:00 ./echoser_fork
simba   3321  0.0  0.0  4392  836 pts/3   S+  22:13  0:00 grep --color=auto echoser

如果把29行代码注释掉，上述的情景输出可能为：

simba@ubuntu:~$ ps aux | grep echoser
simba   3125  0.0  0.0  2004  280 pts/0   S+  21:38  0:00 ./echoser_fork
simba   3128  0.0  0.0    0   0 pts/0   Z+  21:38  0:00 [echoser_fork] <defunct>
simba   3130  0.0  0.0  2004   60 pts/0   S+  21:38  0:00 ./echoser_fork
simba   3141  0.0  0.0  4392  832 pts/3   S+  21:40  0:00 grep --color=auto echoser

即子进程退出后变成了僵尸进程。

如果不想忽略SIGCHLD信号，则必须在信号处理函数中调用wait处理，但这里需要注意的是wait只能等待第一个退出的子进程，所以这里需要使用

waitpid。若当前只有一个子进程退出，则waitpid一次之后因为其他子进程状态尚未改变，故返回0退出循环；若几个连接同时断开，信号因为不能排队

而只接收到一个SIGCHLD信号，waitpid多次之后已经不存在子进程了，返回-1退出循环。如下所示：

```
signal(SIGCHLD, handler);
.....................

void handler(int sig)
{
    /*  wait(NULL); //只能等待第一个退出的子进程 */
 
    while (waitpid(-1, NULL, WNOHANG) > 0)
        ;
}
```

二、在[最基本的回射客户/服务器程序](http://blog.csdn.net/simba888888/article/details/9025581)中，服务器只能被动接收客户端的信息，而不能主动发送信息给客户端**，如果我们想实现对等通信，即P2P，可以在服务器程序用使用两个进程**，一个进程接收用户的输入并发送给客户端，另一个进程被动接收客户端的消息并打印出来，此进程当read 返回0 时得知 客户端已经关闭需要退出进程，此时尚有另一个进程未退出，可以通过在退出前发送消息给它，在消息处理函数中退出。当然客户端也必须使用双进程，原理与服务器程序相同。

```c
/*************************************************************************
    > File Name: echoser.c
    > Author: Simba
    > Mail: dameng34@163.com
    > Created Time: Fri 01 Mar 2013 06:15:27 PM CST
 ************************************************************************/

#include<stdio.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<stdlib.h>
#include<errno.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#include<string.h>
#include<signal.h>

#define ERR_EXIT(m) \
    do { \
        perror(m); \
        exit(EXIT_FAILURE); \
    } while (0)

void handler(int sig)
{
    printf("recv a sig=%d\n", sig);
    exit(EXIT_SUCCESS);
}

int main(void)
{
    int listenfd; //被动套接字(文件描述符），即只可以accept
    if ((listenfd = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
        //  listenfd = socket(AF_INET, SOCK_STREAM, 0)
        ERR_EXIT("socket error");

    struct sockaddr_in servaddr;
    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(5188);
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
    /* servaddr.sin_addr.s_addr = inet_addr("127.0.0.1"); */
    /* inet_aton("127.0.0.1", &servaddr.sin_addr); */

    int on = 1;
    if (setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0)
        ERR_EXIT("setsockopt error");

    if (bind(listenfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0)
        ERR_EXIT("bind error");

    if (listen(listenfd, SOMAXCONN) < 0) //listen应在socket和bind之后，而在accept之前
        ERR_EXIT("listen error");

    struct sockaddr_in peeraddr; //传出参数
    socklen_t peerlen = sizeof(peeraddr); //传入传出参数，必须有初始值
    int conn; // 已连接套接字(变为主动套接字，即可以主动connect)
    if ((conn = accept(listenfd, (struct sockaddr *)&peeraddr, &peerlen)) < 0)
        ERR_EXIT("accept error");
    printf("recv connect ip=%s port=%d\n", inet_ntoa(peeraddr.sin_addr),
           ntohs(peeraddr.sin_port));

    pid_t pid;
    pid = fork();
    if (pid == -1)
        ERR_EXIT("fork error");
    if (pid == 0)
    {

        signal(SIGUSR1, handler);

        char sendbuf[1024] = {0};
        while (fgets(sendbuf, sizeof(sendbuf), stdin) != NULL)
        {
            write(conn, sendbuf, strlen(sendbuf));
            memset(sendbuf, 0, sizeof(sendbuf));
        }
        exit(EXIT_SUCCESS);
    }
    else
    {
        char recvbuf[1024];
        while (1)
        {
            memset(recvbuf, 0, sizeof(recvbuf));
            int ret = read(conn, recvbuf, sizeof(recvbuf));
            if (ret == -1)
                ERR_EXIT("read error");
            else if (ret == 0)
            {
                printf("peer close\n");
                break;
            }
            fputs(recvbuf, stdout);
        }
        kill(pid, SIGUSR1); //父进程退出时发送信号给子进程
        exit(EXIT_SUCCESS);
    }


}
```

客户端程序与服务器端程序的改变差不多，就不贴了，这里是使用父子进程来完成对等通信，即双方都可以发送信息给对方，也可以接收对方的信息，

上面使用了kill 函数来发现自定义信号，如果子进程发送信号给父进程，可以使用getppid 函数得到父进程的id。输出如下：

simba@ubuntu:~/Documents/code/linux_programming/UNP/socket$ ./p2pser
recv connect ip=127.0.0.1 port=56404
fsafd      //<< server 输入
fds


^C

simba@ubuntu:~/Documents/code/linux_programming/UNP/socket$ ./p2pcli
fsafd
fds       //<< client 输入


peer close
recv a sig=10



注意，ctrl+c 会将前台进程组中的两个进程都终止掉，即父子进程都打开了conn，只有两个进程都close(conn)，将file 的引用计数减为0，才会真正关

闭sockfs 文件，这样client 其中一个进程才能read 返回0打印peer close，并发信号给另一个进程，在信号处理函数中退出。