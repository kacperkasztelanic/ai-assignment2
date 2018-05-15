#!/bin/bash
for i in {1..32}
do
   >&2 echo BackTracking first solution: $i
   java -jar ../target/csp.jar -p=NQ -m=B -s=$i
done

for i in {1..32}
do
   >&2 echo ForwardChecking first solution: $i
   java -jar ../target/csp.jar -p=NQ -m=F -s=$i
done

for i in {1..15}
do
   >&2 echo BackTracking all solutions: $i
   java -jar ../target/csp.jar -p=NQ -m=B -s=$i -a
done

for i in {1..15}
do
   >&2 echo ForwardChecking all solutions: $i
   java -jar ../target/csp.jar -p=NQ -m=F -s=$i -a
done
