# JContacts
> [Official GitHub Page](https://0x1c1b.github.io/JContacts/) - A simple Java based solution for the organization of contacts

## Table of Contents

- [Overview](#overview)
- [Installation](#installation)
  - [Debian](#debian)
  - [Windows](#windows)
- [Usage](#usage)
- [License](#license)
  - [Forbidden](#forbidden)
  - [Third-party](#third-party)

## Overview

Originally this was an educational project. It was the intention of this project to unite many different components,
such as databases, to achieve the greatest possible studying success within one single application. Please note that this
application is still in developing and should be used carefully.

In the current state, this application contains all basic features for managing contacts. The single contacts will stored in a local standalone database. Moreover, it's possible to store, edit, export and delete single contacts. In future versions there will be a search function, to extract single contacts from database by tags. Also, there will be multi language support, at the moment for english, french and german.

| ![Preview Image](https://github.com/0x1C1B/JContacts/raw/master/doc/img/preview.png) | 
|:--:| 
| *Preview image of application* |

## Installation

There is no need to install external dependencies, just extract the downloaded zip/tar.gz file to the installation directory, maybe additional software is requiered to extract the downloaded zip file. The directory in which you extract the downloaded file will also be the installation directory.

### Debian

```sh
$ sudo apt-get install unzip
$ unzip JContacts-<version>.zip -d /path/to/installation/directory
```

A further option is to create a desktop entry to launch the jar directly from menu:

```
[Desktop Entry]
Name=JContacts
Exec=java -jar '/path/to/installation/directory/JContacts-<version>.jar' 
Icon=your/path/to/icon
Terminal=false
Type=Application
```

Finally store the created desktop entry at `~/.local/share/applications/`.

### Windows

Maybe you need additional software to extract the downloaded archiv on this system. If you need additional software, here are two possible applications:

- [7-Zip](https://www.7-zip.org/) - Provides a graphical user interface
- [UnZip](http://gnuwin32.sourceforge.net/packages/unzip.htm) - Mostly command-line based

However, extract the downloaded file to the installation directory. Equivalent to Debian systems it is possible to create a desktop shortcut:

```cmd
$ mklink "path/to/shortcut/directory/myShortcut" "/path/to/installation/directory/JContacts-<version>.jar"
```

## Usage

The installation root directory contains a jar file like `JContacts-<version>.jar`. Execute this jar file for launching the
application.

```sh
$ java -jar JContacts-<version>.jar
```

Alternate if your system supports it, you can open/execute the jar file from file manager. If you have multiple options to open the jar file from file manager, it's important to open it with the JRE.

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
