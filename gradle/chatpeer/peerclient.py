from socket import *
serverName = 'localhost'
serverPort = 12000
clientSocket = socket(AF_INET, SOCK_DGRAM)

while 1:
 message = raw_input('enter')
 if (message == "exit"):
  clientSocket.sendto(message,(serverName,serverPort))
  break
 clientSocket.sendto(message,(serverName,serverPort))
 modifiedMessage , serverAddress = clientSocket.recvfrom(2048)
 print modifiedMessage
 
 if (modifiedMessage == "exit"):
  print "bye"
  break
clientSocket.close()



