# gRPC Tic Tac Toe exercise

This is the starting point for the gRPC Tic Tac Toe exercise.

The project is composed by three modules:
- [contract](contract/) - protocol buffers definition
- [server](server/) - implementation of service
- [client](client/) - invocation of service

See the README for each module.
Start at contract, then go to server, and finally go to the client.

Resolucao P3:

Primeiro foi necessario adicionar os metodos ao proto no diretorio
contract, adicionando as mensagens de jogo e de verificacao de
vencedor a cada jogada, seguido com a declaracao de servicos.

Depois passou-se a implementacao na parte do servidor, onde no
ficheiro TTTServiceImpl.java se definiram os metodos com o qual
o cliente comunica com o servidor, mais precisamente o comando
de jogada por parte de cada jogador e a verificacao da condicao
de vencedor/empate.

Por fim, passou-se as chamadas feitas por parte do cliente
conforme a interface definida no proto e no servidor. Isto
passar por pegar no stub (ligacao ja implementada ao servidor,
assincrona) e tornar a logica do jogo trabalho do servidor,
enquanto que a validacao de input continua a ser feita pelo
cliente.


----

## Authors

**Group T45**

[100721](mailto:antonio.moreira.oliveira@tecnico.ulisboa.pt)

[97343](mailto:thomas.theisen@tecnico.ulisboa.pt)

[96840](mailto:antonio.oliveira.fernandes@tecnico.ulisboa.pt)


----

For help, please contact:

[SD Faculty](mailto:leic-sod@disciplinas.tecnico.ulisboa.pt)
