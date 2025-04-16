---
# Variables de entorno
## Agregue las variables de entorno para mas seguridad en ocasiones futuras, no es necesario modificar el archivo **hibernate.cfg.xml** ya que esto estara seteado automaticamente una vez que agreguen las variables de entorno. Para poder asignar los datos de sus bd tenemos que hacer lo siguiente.
1. **Click derecho en cualquier clase** --> **Run As** --> **Run Configurations**
2. Dirigirse a la pestaña **Arguments**
3. Abajo aparecera algo llamado **VM Arguments** y copiar lo siguiente:
```java
-DDRIVER=com.mysql.cj.jdbc.Driver
-DDB=jdbc:mysql://localhost:3306/(su-schema)?serverTimezone=UTC
-DUSER=(usuario)
-DPASSWORD=(contraseña)
```
---
