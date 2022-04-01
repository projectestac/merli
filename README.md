**Merli**

Catalog of educational resources classified by LOM metadata

**Configuració entorn local**

1. Configurar en el path el ant, per exemple:

```
export PATH=/opt/apache-ant-1.9/apache-ant-1.9.16/bin:$PATH
```

2. Configurar en el path la jdk, per exemple:

```
export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_79/
```

**Creació ears PRE**

Executar:

```
ant dist-pre
```

Aquesta task executa la resta de tasques dels diferents mòduls agafant les propietats per l'entorn de pre

Abans de compilar realitza un rename de les dependències \*.aar a \*.jar, per a ser pujades al git del SIC

**Creació ears PRO**

Executar:

```
ant dist-pro
```

Aquesta task executa la resta de tasques dels diferents mòduls agafant les propietats per l'entorn de pre

Abans de compilar realitza un rename de les dependències \*.aar a \*.jar, per a ser pujades al git del SIC