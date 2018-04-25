# JContacts

> A simple Java based solution for the organization of contacts

## Table of Contents

- [Overview](#overview)
  - [Features](#features)
- [Installation](#installation)
  - [Dependencies](#dependencies)
  - [Debian](#debian)
  - [Windows](#windows)
- [Usage](#usage)
- [License](#license)
  - [Forbidden](#forbidden)
  - [Third-party](#third-party)

## Overview

Originally this was an educational project. The intention of this project is to unite many different components,
such as databases, to achieve the greatest possible studying success within one single application. Please note that this
application is still in developing and should be used carefully. You can also visit the [Official Website](https://0x1c1b.github.io/JContacts/).

In the current state, this application contains all basic features for managing contacts. The single contacts will stored in a local standalone database. Moreover, general features, like editing or exporting single contacts, are already implemented. In future versions there will be full multi language support. At the moment for english, french and german.

| ![Preview Image](https://github.com/0x1C1B/JContacts/raw/master/doc/img/preview.png) | 
|:--:| 
| *Preview image of application* |

### Features

- Multi language support (partial: English, German, French)
- Export/Import contacts to/from XML format
- Start default browser or mail client directly from application
- All general editing options (store, save, edit, ...)
- All contacts are stored in a local standalone database

## Installation

Just extract the downloaded zip/tar.gz file to the installation directory. Maybe additional software is requiered to extract the downloaded zip/tar.gz file. The directory in which you extract the downloaded file will also be the installation directory.

### Dependencies

To run this application later, it's required to install the Java Virtual Maschine. If you already have installed the JVM you can skip this step. Otherwise you should install the JVM now. You can download the **JRE** package, which contains the needed JVM, from the official [Oracle Website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

On Debian-like systems you can alternate install the OpenJDK instead of the official Oracle version:

```sh
$ sudo apt-get install openjdk-<version>-jre
```

### Debian

```sh
$ sudo apt-get install unzip
$ unzip JContacts-<version>.zip -d /path/to/installation/directory
```

A further option is creating a desktop entry to launch the jar directly from menu:

```
[Desktop Entry]
Name=JContacts
Exec=java -jar '/path/to/installation/directory/JContacts-<version>.jar' 
Icon=/path/to/application/icon
Terminal=false
Type=Application
```

Finally store the created desktop entry at `~/.local/share/applications/` as `JContacts.desktop`.

### Windows

Maybe you need additional software to extract the downloaded archiv on this system. If you need additional software, here are two possible applications:

- [7-Zip](https://www.7-zip.org/) - Provides a graphical user interface
- [UnZip](http://gnuwin32.sourceforge.net/packages/unzip.htm) - Mostly command-line based

However, extract the downloaded file to the installation directory. Equivalent to Debian systems it is possible to create a desktop shortcut:

```cmd
$ mklink "C:\path\to\shortcut\directory\myShortcut" "C:\path\to\installation\directory\JContacts-<version>.jar"
```

## Usage

The installation root directory contains a jar file like `JContacts-<version>.jar`. Execute this jar file for launching the
application.

```sh
$ java -jar JContacts-<version>.jar
```

Alternate if your system supports it, you can open/execute the jar file from file manager or menu. If you have multiple options to open the jar file, you should ensure that you open it with the JVM.

## License

Copyright (c) 2018 0x1C1B

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[MIT License](https://opensource.org/licenses/MIT) or [LICENSE](LICENSE) for
more details.

### Forbidden

**Hold Liable**: Software is provided without warranty and the software
author/license owner cannot be held liable for damages.

### Third-party

The icons, sounds, and photos used in this software are extracted from [Icons8](https://icons8.com/) and are published
under the CC Attribution-NoDerivs 3.0 License.

[CC Attribution-NoDerivs 3.0 License](https://creativecommons.org/licenses/by-nd/3.0/) for
more details.
