#!/bin/bash

mysql -h 192.168.99.1 -u root -pr00t -P 3306 < scripts/sql/init-database.sql
