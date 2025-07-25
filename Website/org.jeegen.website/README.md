How to Test the Website
=======================

The website is generated with [Jekyll](http://jekyllrb.com).

On Ubuntu you have to install some packages using:
```
sudo apt-get install ruby-rubygems ruby-dev
```

For installing some Ruby Gems you have to put some environment veriables
into your .bashrc:
```
export GEM_HOME="$HOME/gems"
export PATH="$GEM_HOME/bin:$PATH"
```

Then you have to install the Jekyll Ruby Gem:

```
gem install jekyll
```
To generate the website, run
```
  jekyll build
```
You will find the result in the `_site` folder. You can test it locally with
```
  jekyll serve
```
which will start a [server](http://127.0.0.1:4000) displaying the site. The server also listens for file changes and
automatically rebuilds the website.

Ruby Troubleshooting
--------------------

- On Linux/Mac OS we recommend using [RVM](https://rvm.io/) to manage your Ruby installations
- On Windows you will have to install the [development kit](https://github.com/oneclick/rubyinstaller/wiki/Development-Kit)