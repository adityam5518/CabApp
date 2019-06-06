from socket import *
serverPort = 12000
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('',serverPort))
print "Server ready......"
while 1:
 message, clientAddress = serverSocket.recvfrom(2048)
 print message

 if (message == "exit"):
  print "bye"
  break

 sendmessage = raw_input('enter')
 if (sendmessage == "exit"):
  serverSocket.sendto(sendmessage, clientAddress)
  break
 serverSocket.sendto(sendmessage, clientAddress)
 
serverSocket.close()
