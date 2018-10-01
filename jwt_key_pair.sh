#!/bin/bash

# ref https://stackoverflow.com/a/19387517/5824101

# gen key pair in PEM format
openssl genrsa -out private_key.pem 2048
openssl rsa -pubout -in private_key.pem -out public_key.pem

# Convert private Key to PKCS#8 format (so Java can read it)
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

# Output public key portion in DER format (so Java can read it)
openssl rsa -inform PEM -in private_key.pem -pubout -outform DER -out public_key.der
