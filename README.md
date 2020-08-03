# Encrypt-Decrypt #

This project is developed with "JetBrains Academy" https://hyperskill.org/.

Console program to encode/decode input data. This project displayed how
to encrypt and decrypt messages and texts using simple algorithms. 
You should note that such algorithms are not suitable for industrial use because
they can easily be cracked, but these algorithms demonstrate some general ideas
about encryption.

You may choose different algorithms for encode/decode data. The first one is 
shifting algorithm (it shifts each letter by the specified number according
to its order in the alphabet in circle). The second based on Unicode table.

When starting the program, the necessary algorithm should be specified by
an arguments:
 - -mode enc/dec, if there is no (-mode), the program will be work in enc mode;
 - -data any expression, if there is no (-data), it returns empty string;
 - -in file which from you need encode/decode data, if file do not exist it will be call exception;
 - -out file which to you need write your encode/decode data, file will be created by a program;
 - -alg shift/unicode, if there is no (-alg), default it to shift;
 - -key any digit from 1 to 100, if there is no (-key) or key equals 0, it returns input data;
 
Arguments you may to write in makefile, in "run" line. See examples below.

For starting program you need check arguments in makefile and:
```
$ make #build & run
```
Stack of technologies in project:
- Java Core

### Examples:

###### Example 1:

```java Main -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode```

This command must get data from the file road_to_treasure.txt, encrypt the data with the key 5,
create a file called protected.txt and write ciphertext to it. File road_to_treasure.txt is already exist in the corner catalog.

##### Example 2

###### Input:

```java Main -mode enc -key 5 -data "Welcome to hyperskill!" -alg unicode```

###### Output:

```\jqhtrj%yt%m~ujwxpnqq&```

###### Example 3

###### Input:

```java Main -key 5 -alg unicode -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec```

###### Output:

```Welcome to hyperskill!```

###### Example 4:

###### Input:

```java Main -key 5 -alg shift -data "Welcome to hyperskill!" -mode enc```

###### Output:

```Bjqhtrj yt mdujwxpnqq!```

###### Example 5:

###### Input:

```java Main -key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec```

###### Output:

```Welcome to hyperskill!```