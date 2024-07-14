plugins {
    java
    application

    id("io.freefair.lombok") version "8.6"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "moe.qie2035"
version = "0.1.0"

dependencies {
    implementation("com.esotericsoftware:kryo:5.6.0")
    implementation(
        "io.github.mkpaz:atlantafx-base:2.0.1"
    ) { exclude("org.openjfx") }


    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
    google()
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass = "moe.qie2035.hz.Launcher"
}

tasks.compileJava {
    options.encoding = "UTF-8"
}