<?xml version="1.0" encoding="UTF-8"?>
<node type="FastDiagram">
<subnodes>
<node id="0" type="StartNode">
<name>entry</name>
<inputs/>
<outputs>
<node type="FastConnection">
<source id="0" type="StartNode">entry</source>
<target id="3" type="FunctionNode">f3</target>
</node>
</outputs>
</node>
<node id="1" type="FunctionNode">
<name>f1</name>
<inputs>
<node type="FastConnection">
<source id="5" type="JoinpointNode"></source>
<target id="1" type="FunctionNode">f1</target>
</node>
</inputs>
<outputs>
<node type="FastConnection">
<source id="1" type="FunctionNode">f1</source>
<target id="0" type="PredicateNode">p0</target>
</node>
</outputs>
</node>
<node id="2" type="FunctionNode">
<name>f2</name>
<inputs>
<node type="FastConnection">
<source id="3" type="FunctionNode">f3</source>
<target id="2" type="FunctionNode">f2</target>
</node>
</inputs>
<outputs>
<node type="FastConnection">
<source id="2" type="FunctionNode">f2</source>
<target id="5" type="JoinpointNode"></target>
</node>
</outputs>
</node>
<node id="3" type="FunctionNode">
<name>f3</name>
<inputs>
<node type="FastConnection">
<source id="0" type="StartNode">entry</source>
<target id="3" type="FunctionNode">f3</target>
</node>
</inputs>
<outputs>
<node type="FastConnection">
<source id="3" type="FunctionNode">f3</source>
<target id="2" type="FunctionNode">f2</target>
</node>
</outputs>
</node>
<node id="5" type="JoinpointNode">
<name></name>
<inputs>
<node type="FastConnection">
<source id="2" type="FunctionNode">f2</source>
<target id="5" type="JoinpointNode"></target>
</node>
<node type="FastConnection">
<source id="0" type="PredicateNode">p0</source>
<target id="5" type="JoinpointNode"></target>
</node>
</inputs>
<outputs>
<node type="FastConnection">
<source id="5" type="JoinpointNode"></source>
<target id="1" type="FunctionNode">f1</target>
</node>
</outputs>
</node>
<node id="6" type="EndNode">
<name>exit</name>
<inputs>
<node type="FastConnection">
<source id="0" type="PredicateNode">p0</source>
<target id="6" type="EndNode">exit</target>
</node>
</inputs>
<outputs/>
</node>
<node id="0" type="PredicateNode">
<name>p0</name>
<inputs>
<node type="FastConnection">
<source id="1" type="FunctionNode">f1</source>
<target id="0" type="PredicateNode">p0</target>
</node>
</inputs>
<outputs>
<node type="FastConnection">
<source id="0" type="PredicateNode">p0</source>
<target id="6" type="EndNode">exit</target>
</node>
<node type="FastConnection">
<source id="0" type="PredicateNode">p0</source>
<target id="5" type="JoinpointNode"></target>
</node>
</outputs>
</node>
</subnodes>
</node>
