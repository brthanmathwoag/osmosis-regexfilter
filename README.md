# osmosis-regexfilter

This is an [Osmosis](http://wiki.openstreetmap.org/wiki/Osmosis) plugin for filtering entities based on regular expressions.
This allows more finely-grained control than the default tag-filter plugin which accepts only exact key=value or key=* matches.

## Installation

You can grab the latest version from [here](https://drive.google.com/file/d/0B_sU33gr527ZdVgzemFXNkFWU00/view?usp=sharing) (SHA1: 37aba26210ca241ca57e74996cca95e7815832db)
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
