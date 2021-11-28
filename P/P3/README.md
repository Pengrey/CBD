## Keyspace Operations

### Creating a Keyspace

```bash
CREATE KEYSPACE “KeySpace Name”
WITH replication = {'class': ‘Strategy name’, 'replication_factor' : ‘No.Of   replicas’};
```

| Strategy name | Description |
| --- | --- |
| Simple Strategy' | Specifies a simple replication factor for the cluster. |
| Network Topology Strategy | Using this option, you can set the replication factor for each data-center independently. |
| Old Network Topology Strategy | This is a legacy replication strategy. |

Verification

```bash
DESCRIBE keyspaces;
```

### Alter Keyspace

```bash
ALTER KEYSPACE “KeySpace Name”
WITH replication = {'class': ‘Strategy name’, 'replication_factor' : ‘No.Of  replicas’};
```

### Drop Keyspace

```bash
DROP KEYSPACE “KeySpace name”
```

## Table Operations

### Creating a Table

```bash
CREATE (TABLE | COLUMNFAMILY) <tablename>
('<column-definition>' , '<column-definition>')
(WITH <option> AND <option>)
```

Defining a Column

```bash
column name1 data type,
column name2 data type,

example:

age int,
name text
```

### Primary Key

```bash
CREATE TABLE tablename(
   column1 name datatype PRIMARYKEY,
   column2 name data type,
   column3 name data type.
   )
```

or

```bash
CREATE TABLE tablename(
   column1 name datatype PRIMARYKEY,
   column2 name data type,
   column3 name data type,
   PRIMARY KEY (column1)
   )
```

Verification

```bash
select * from tablename;
```

### Alter Table

```bash
ALTER (TABLE | COLUMNFAMILY) <tablename> <instruction>
```

Adding a Column

```bash
ALTER TABLE table name
ADD  new column datatype;
```

Dropping a Column

```bash
ALTER table name
DROP column name;
```

### Dropping a Table

```bash
DROP TABLE <tablename>
```

Verification

```bash
DESCRIBE COLUMNFAMILIES;
```

### Truncate Table

You can truncate a table using the TRUNCATE command. When you truncate a table, all the rows of the table are deleted permanently. Given below is the syntax of this command.

```bash
TRUNCATE <tablename>
```

### Create Index

```bash
CREATE INDEX <identifier> ON <tablename>
```

### Dropping an Index

```bash
DROP INDEX <identifier>
```

### Using Batch Statements

```bash
BEGIN BATCH
<insert-stmt>/ <update-stmt>/ <delete-stmt>
APPLY BATCH
```

## CURD Operations

### Creating Data in a Table

```bash
INSERT INTO <tablename>
(<column1 name>, <column2 name>....)
VALUES (<value1>, <value2>....)
USING <option>
```

### Updating Data in a Table

```sql
UPDATE <tablename>
SET <column name> = <new value>
<column name> = <value>....
WHERE <condition>
```

### Reading Data using Select Clause

```sql
SELECT FROM <tablename>
```

### Where Clause

```sql
SELECT FROM <table name> WHERE <condition>;
```

### Deleting Datafrom a Table

```sql
DELETE FROM <identifier> WHERE <condition>;
```

### Deleting an Entire Row

```sql
DELETE FROM emp WHERE emp_id=3;
```

## CQL Datatypes

| Data Type | Constants | Description |
| --- | --- | --- |
| ascii | strings | Represents ASCII character string |
| bigint | bigint | Represents 64-bit signed long |
| blob | blobs | Represents arbitrary bytes |
| Boolean | booleans | Represents true or false |
| counter | integers | Represents counter column |
| decimal | integers, floats | Represents variable-precision decimal |
| double | integers | Represents 64-bit IEEE-754 floating point |
| float | integers, floats | Represents 32-bit IEEE-754 floating point |
| inet | strings | Represents an IP address, IPv4 or IPv6 |
| int | integers | Represents 32-bit signed int |
| text | strings | Represents UTF8 encoded string |
| timestamp | integers, strings | Represents a timestamp |
| timeuuid | uuids | Represents type 1 UUID |
| uuid | uuids | Represents type 1 or type 4 |
|  |  | UUID |
| varchar | strings | Represents uTF8 encoded string |
| varint | integers | Represents arbitrary-precision integer |

### Collection Types

| Collection | Description |
| --- | --- |
| list | A list is a collection of one or more ordered elements. |
| map | A map is a collection of key-value pairs. |
| set | A set is a collection of one or more elements. |

### User-defined datatypes

Cqlsh provides users a facility of creating their own data types. 
Given below are the commands used while dealing with user defined datatypes.

- **CREATE TYPE** − Creates a user-defined datatype.
- **ALTER TYPE** − Modifies a user-defined datatype.
- **DROP TYPE** − Drops a user-defined datatype.
- **DESCRIBE TYPE** − Describes a user-defined datatype.
- **DESCRIBE TYPES** − Describes user-defined datatypes.

## CQL Collections

### Creating a Table with List

```sql
CREATE TABLE data(name text PRIMARY KEY, email list<text>);
```

### Inserting Data into a List

```sql
INSERT INTO data(name, email) VALUES ('ramu', ['abc@gmail.com','cba@yahoo.com']);
```

### Updating a List

```sql
UPDATE data SET email = email +['xyz@tutorialspoint.com'] WHERE name = 'ramu';
```

### Verification

```sql
SELECT * FROM data;
```

## SET

### Creating a Table with Set

```sql
CREATE TABLE data2 (name text PRIMARY KEY, phone set<varint>);
```

### Inserting Data into a Set

```sql
INSERT INTO data2(name, phone)VALUES ('rahman', {9848022338,9848022339});
```

### Updating a Set

```sql
UPDATE data2 SET phone = phone + {9848022330} WHERE name = 'rahman';
```

### Verification

```sql
SELECT * FROM data2;
```

## MAP

### Creating a Table with Map

```sql
CREATE TABLE data3 (name text PRIMARY KEY, address map<text, text>);
```

### Inserting Data into a Map

```sql
INSERT INTO data3 (name, address) VALUES ('robin', {'home' : 'hyderabad' , 'office' : 'Delhi' } );
```

### Updating a Set

```sql
UPDATE data3 SET address = address+{'office':'mumbai'} WHERE name = 'robin';
```

### Verification

```sql
SELECT * FROM data3;
```