# There is 3 ways to generate RSA256 public and private key for JAVA
1. using `keytool`
1. using `openssl genrsa`
1. using `ssh-agent`

Those tools achieve same propose as following same encypt spec but producing different file formats. Also `keytool` and `ssh-agent` providing passphase to protect private key.

## keytool
this is tool specify for JAVA, the tool will create `keystore` which is JAVA concept that JAVA can easily process the key

reference:

[difference-between-keytool-and-openssl](https://stackoverflow.com/questions/48472764/difference-between-keytool-and-openssl)
[Difference between OpenSSL and keytool](https://security.stackexchange.com/questions/98282/difference-between-openssl-and-keytool)
[Java JWT Token Tutorial using JJWT Library](https://www.codeproject.com/Articles/1253786/Java-JWT-Token-Tutorial-using-JJWT-Library)

## openssl genrsa
most [common tool](https://wiki.openssl.org/index.php/Command_Line_Utilities#rsa_.2F_genrsa) to create encryption key, default create key in `.pem` format with base64Encode the binay key resulting 3 part: header with `-----BEGIN RSA PRIVATE KEY-----`, body with base64Encode string, footer with `-----END RSA PRIVATE KEY-----`

## ssh-agent
tool normally for creating private/ public key for ssh access

[What are the differences between ssh generated keys(ssh-keygen) and OpenSSL keys (PEM)and what is more secure for ssh remote login?](https://security.stackexchange.com/questions/29876/what-are-the-differences-between-ssh-generated-keysssh-keygen-and-openssl-keys)

## How to load different type of generated key using JAVA
if you using `keytool` generating `.jks` file will be the most JAVA way, follow [this](https://www.codeproject.com/Articles/1253786/Java-JWT-Token-Tutorial-using-JJWT-Library), but less compatible with other tools, need step to convert file to `.pem` ..etc format.

if using `openssl genrsa` or `ssh-agent` to create `.pem` refer [this](https://stackoverflow.com/questions/11787571/how-to-read-pem-file-to-get-private-and-public-key), that need more JAVA code to handle. Also we need encode private key to `pkcs8` format `.pem` file to support JAVA.

we can also use `openssl genrsa` to convert `.pem` file to `.der` file refer [this](https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file), this way need less JAVA code to handle so will be less overhead.This is same as `.pem` that we need encode private key to `pkcs8` format but output `.der` file to support JAVA.

## Test
1. `sh jwt_key_pair.sh` generated public/ private key
1. replace `privateKeyPath` and `publicKeyPath` in App.java
1. run App.java