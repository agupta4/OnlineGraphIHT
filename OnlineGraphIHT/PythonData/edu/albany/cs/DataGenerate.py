'''
Created on Nov 15, 2016

@author: amrinder
'''
import random
sampleSize=100
random.seed()
realRandomVariates=[]
def trunc_gauss(mu, sigma, bottom, top):
    a = random.gauss(mu,sigma)
    while (bottom <= a <= top) == False:
        a = random.gauss(mu,sigma)
    return a
for i in range(sampleSize):
    
    #newVal=num = min(10, max(0, random.gauss(3, 4)))
    newVal=trunc_gauss(5,10,0,10)
    realRandomVariates.append(newVal)
    
print (realRandomVariates)
