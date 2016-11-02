# BasicDB
a java implementation of database system
a basic database management system called SimpleDB.
focus on implementing the core modules required to access stored data on disk;
in future labs, add support for various query processing operators, as well as transactions, locking, and concurrent queries.

The system consists of:

Classes that represent fields, tuples, and tuple schemas;
Classes that apply predicates and conditions to tuples;
One or more access methods (e.g., heap files) that store relations on disk and provide a way to iterate through tuples of those relations;
A collection of operator classes (e.g., select, join, insert, delete, etc.) that process tuples;
A buffer pool that caches active tuples and pages in memory and handles concurrency control and transactions
A catalog that stores information about available tables and their schemas.
SimpleDB does not include many things that you may think of as being a part of a "database."

In future sessions, it will include

- A SQL front end or parser that allows you to type queries directly into SimpleDB. 
- Data types except integers and fixed length strings.
- Query optimizer.
- Indices.
