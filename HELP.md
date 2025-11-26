# Guía rápida para .gitignore en proyectos Spring Boot / Maven

Usa este contenido como base para tu archivo `.gitignore` en proyectos Java / Spring Boot.

```gitignore
### Maven / Spring Boot
target/
!.mvn/wrapper/maven-wrapper.jar

### STS (Spring Tool Suite)
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA
.idea/
*.iml
*.iws
*.ipr
out/

### NetBeans
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/

### Gradle (por si usas)
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code
.vscode/

### macOS
.DS_Store

### Logs
*.log
