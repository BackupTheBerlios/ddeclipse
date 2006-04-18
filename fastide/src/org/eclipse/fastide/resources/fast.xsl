<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" />
	<xsl:template match="/">
		<xsl:apply-templates select="node/subnodes/node" mode="all" />
	</xsl:template>
	<xsl:template select="node/subnodes/node" mode="all">
		<xsl:variable name="nodeCount" select="count(outputs/node/target[@type != 'JoinpointNode'])" />
		<xsl:if test="@type != 'JoinpointNode' and $nodeCount != 0">
		<xsl:variable name="nodeType" select="@type" />
		<xsl:variable name="nodeName" select="name" />
		<xsl:for-each select="outputs/node">
			<xsl:choose>
				<xsl:when test="$nodeType = 'StartNode'">
					<xsl:text>entry</xsl:text>
				</xsl:when>
				<xsl:when test="$nodeType = 'FunctionNode'">
					<xsl:text>[</xsl:text>
					<xsl:text><xsl:value-of select="$nodeName" /></xsl:text>
					<xsl:text>]</xsl:text>
				</xsl:when>
				<xsl:when test="$nodeType = 'PredicateNode'">
					<xsl:text>&lt;</xsl:text>
					<xsl:text><xsl:value-of select="$nodeName" /></xsl:text>
					<xsl:text>&gt;</xsl:text>
				</xsl:when>
				<xsl:when test="$nodeType = 'JoinpointNode'"></xsl:when>
				<xsl:otherwise>
					<xsl:text>exit</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>=</xsl:text>
			<xsl:text disable-output-escaping="yes">></xsl:text>
			<xsl:choose>
				<xsl:when test="target[@type = 'StartNode']">
					<xsl:text>entry</xsl:text>
				</xsl:when>
				<xsl:when test="target[@type = 'FunctionNode']">
					<xsl:text>[</xsl:text>
					<xsl:text><xsl:value-of select="target" /></xsl:text>
					<xsl:text>]</xsl:text>
				</xsl:when>
				<xsl:when test="target[@type = 'PredicateNode']">
					<xsl:text>&lt;</xsl:text>
					<xsl:text><xsl:value-of select="target" /></xsl:text>
					<xsl:text>&gt;</xsl:text>
				</xsl:when>
				<xsl:when test="target[@type = 'JoinpointNode']">
					<xsl:apply-templates select="node/subnodes/node" mode="joinpoint">
						<xsl:param name="nodeId" select="target[@id]" />
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>exit</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>&#13;</xsl:text>
		</xsl:for-each>
		</xsl:if>
	</xsl:template>
	<xsl:template select="node/subnodes/node" mode="joinpoint">
		
	</xsl:template>
</xsl:stylesheet>