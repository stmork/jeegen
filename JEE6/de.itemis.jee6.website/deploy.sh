#!/bin/bash

scp -r ${PWD}/website/* root@updates.itemis.de:/data/jee6-generator/

echo "Website deployed"