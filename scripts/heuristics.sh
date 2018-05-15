#!/bin/bash
for i in {1..35}
do
   >&2 echo BackTracking both first solution: $i
   java -jar target/csp.jar -p=NQ -m=B -s=$i -H=var -H=val
done

for i in {1..14}
do
   >&2 echo BackTracking both all solutions: $i
   java -jar target/csp.jar -p=NQ -m=B -s=$i -a -H=var -H=val
done

for i in {1..29}
do
   >&2 echo BackTracking var first solution: $i
   java -jar target/csp.jar -p=NQ -m=B -s=$i -H=var
done

for i in {1..14}
do
   >&2 echo BackTracking var all solutions: $i
   java -jar target/csp.jar -p=NQ -m=B -s=$i -a -H=var
done

for i in {1..75}
do
   >&2 echo BackTracking val first solution: $i
   java -jar target/csp.jar -p=NQ -m=B -s=$i -H=val
done

for i in {1..14}
do
   >&2 echo BackTracking val all solutions: $i
   java -jar target/csp.jar -p=NQ -m=B -s=$i -a -H=val
done

