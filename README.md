# osmosis-regexfilter

This is an [Osmosis](http://wiki.openstreetmap.org/wiki/Osmosis) plugin for filtering entities based on regular expressions.
This allows more finely-grained control than the default tag-filter plugin which accepts only exact key=value or key=* matches.
 
## Usage

Build the jar and copy it to your plugins directory:

```
git clone https://github.com/brthanmathwoag/osmosis-regexfilter.git
cd osmosis-regexfilter
mvn package
mkdir -p ~/.openstreetmap/osmosis/plugins/
cp target/osmosis-regexfilter.jar ~/.openstreetmap/osmosis/plugins/
```

Then reference it with `regex-filter` or `rf` aliases in your workflow.
For example, to get all entities with any 'addr' subkey set, try the following:

```
osmosis --read-pbf germany-latest.osm.pbf \
    --regex-filter 'addr:.*=.*' \
    --out-xml addresses.xml
```
