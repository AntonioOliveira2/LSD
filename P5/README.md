# DistLedger

Distributed Systems Project 2022/2023

## Description

Initially, the ServiceEntry and ServerEntry classes were created, to form an hierarchy of the following order:
NameServerState -> ServiceEntry -> ServerEntry. The NameServerState class holds a map of all the services that exist,
where ServiceEntries are associated with their name. ServiceEntry themselves have a list of ServerEntries, where such
entries comprise of a host+port and qualifier (the latter having the possible values of primary or secondary).

Furthermore, there are also three new operations: register, where a server gives its service, host+port and qualifier,
and the NameServerState adds it to a ServiceEntry list; lookup, a function that clients can call, returning either a list
with the servers that provide a certain service or possess an asked qualifier, or an empty list in case both of those are
invalid. Atlast there is the delete operation, where servers can request their registry on the NameServer to be deleted.

On top of the described functions, additions to the proto skeleton were made to support the calls to the NamingServer.


## Authors

**Group T45**

### Team Members

| Number | Name                 | User                               | Email                                                  |
|--------|-------------------|---------------------------------------|--------------------------------------------------------|
| 96840  | António Fernandes | <https://github.com/aofcoelho>        | <mailto:antonio.oliveira.fernandes@tecnico.ulisboa.pt> |
| 97367  | Tomas Theisen     | <https://github.com/tomastheisen>     | <mailto:tomas.theisen@tecnico.ulisboa.pt>              |
| 100721 | António Oliveira  | <https://github.com/AntonioOliveira2> | <mailto:antonio.moreira.oliveira@tecnico.ulisboa.pt>   |

## Getting Started

The overall system is made up of several modules. The main server is the _DistLedgerServer_. The clients are the _User_ 
and the _Admin_. The definition of messages and services is in the _Contract_. The future naming server
is the _NamingServer_.

See the [Project Statement](https://github.com/tecnico-distsys/DistLedger) for a complete domain and system description.

### Prerequisites

The Project is configured with Java 17 (which is only compatible with Maven >= 3.8), but if you want to use Java 11 you
can too -- just downgrade the version in the POMs.

To confirm that you have them installed and which versions they are, run in the terminal:

```s
javac -version
mvn -version
```

### Installation

To compile and install all modules:

```s
mvn clean install
```

## Built With

* [Maven](https://maven.apache.org/) - Build and dependency management tool;
* [gRPC](https://grpc.io/) - RPC framework.
