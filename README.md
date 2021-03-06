# osmosis-regexfilter

![build 
status](https://travis-ci.org/brthanmathwoag/osmosis-regexfilter.svg?branch=master)

This is an [Osmosis](http://wiki.openstreetmap.org/wiki/Osmosis) plugin for filtering entities based on regular expressions.
This allows more finely-grained control than the default tag-filter plugin which accepts only exact key=value or key=* matches.

## Installation

You can grab the latest version from [here](https://tznvy.eu/download/osmosis-regexfilter.jar) (SHA1: 37aba26210ca241ca57e74996cca95e7815832db)
Put it in `~/.openstreetmap/osmosis/plugins/` and you are ready to go.

To build it yourself, run:

```
git clone https://github.com/brthanmathwoag/osmosis-regexfilter
cd osmosis-regexfilter
mvn install
```

## Usage

You can reference the plugin in your workflow with `regex-filter` or `rf` aliases.
For example, to get all nodes with any 'addr' subkey set, try the following:

```
osmosis --read-pbf germany-latest.osm.pbf \
    --tag-filter reject-ways \
    --tag-filter reject-relations \
    --regex-filter accept-nodes 'addr:.*=.*' \
    --out-xml addresses.xml
```
