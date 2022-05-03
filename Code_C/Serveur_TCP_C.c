#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/socket.h>
#include <strings.h>
#include <string.h>

int main(int argc, char *argv[])   {
  int s_ecoute,scom, lg_app,i,j;
  struct sockaddr_in adr;
  struct sockaddr_storage recep;
  char buf[1500], renvoi[1500], host[1024],service[20];

   s_ecoute=socket(AF_INET,SOCK_STREAM,0);
   printf("creation socket\n");
   
   adr.sin_family=AF_INET;
   adr.sin_port=htons(atoi(argv[1]));
   adr.sin_addr.s_addr=INADDR_ANY;
   
   if (bind(s_ecoute,(struct sockaddr *)&adr,sizeof(struct sockaddr_in)) !=0) 
   {
     printf("probleme de bind sur v4\n");
     exit(1); 
    }

   if (listen(s_ecoute,5) != 0)      {
       printf("pb ecoute\n"); exit(1);
    }

   printf("en attente de connexion\n");
   while (1)  
   {
      scom=accept(s_ecoute,(struct sockaddr *)&recep, (socklen_t * restrict)&lg_app);
      getnameinfo((struct sockaddr *)&recep,sizeof (recep), host, sizeof(host),service, sizeof(service),0);
       printf("recu de %s venant du port %s\n",host, service);

	   while (1)     
       {
	       recv(scom,buf,sizeof(buf),0);
           printf("buf recu %s\n",buf);
           bzero(renvoi,sizeof(renvoi));
           printf("%d",strlen(buf));
               
            if(buf[0] == '0')
            {
                for(i=strlen(buf)-1,j=0;i>=1;i--,j++)
               {
                   renvoi[j]=buf[i];
               }
	            renvoi[j+1]='\0';
            }
            else if(buf[0] == '1')
            {
                char temp;
                for(i=1 ;i<=strlen(buf) ;i++)
               {
                   renvoi[i-1]=buf[i];
               }

                for (i = 0; i < strlen(renvoi)-2; i++) {
                    for (j = i+1; j < strlen(renvoi)-1; j++) {
                        if (renvoi[i] > renvoi[j]) {
                            temp = renvoi[i];
                            renvoi[i] = renvoi[j];
                            renvoi[j] = temp;
                        }
                    }
                }
                renvoi[j+1] = '\0';
            }
            else if(buf[0] == '2')
            {
                sprintf(renvoi,"%d",strlen(buf));
                renvoi[strlen(buf)]='\0'; 
            }
	       
	       send(scom,renvoi,strlen(renvoi),0);
	       bzero(buf,sizeof(buf));
	       if (strcmp(renvoi,"NIF") == 0)
           {
               break;   
           }
       } 
	   close(scom);   
    }
	close(s_ecoute); 
}

