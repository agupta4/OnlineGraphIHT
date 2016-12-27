Online_Graph_IHT

- This algorithm works for data/nodes in streaming environment. Nodes will appear one by one and 
we apply our algorithm to work around data for refined results.
- The input will be APDM formatted nodes and edges where nodes will represent roads in the blocks 
with their respective crime rates.
- So, far we have applied non parametric scan stat function as an example in the form of

	f(x, S) = -(w^Tx+w'^Tx)/1^Tx
	
	where, w belongs to S and w' belongs to Sbar.
	
	The relation between S and Sbar is
	
	Sbar = V - S	Here, V is ground set of nodes.
	
- For experimentation, the simulation file1 consists of 9 ground set of nodes where a node is a 
road/block in a city. The main purpose for OnlineGraphIHT is to find out anomalous set of nodes 
or blocks in an area which are having high crime rates. The same concept can be applied for civil 
unrest, disease outbreak detection, etc. 



