import random

T = 50
L = ["Landed", "On Time", "Arrived", "Late"]
print("ID,", "Status,", "Price")
while ( T > 0 ):
	print(random.randint(1,10**5),end=', ')
	a = random.randint(0,3)
	# print("Value of a: ",a)
	print(L[a], end=', ')
	print(random.uniform(1,10**5))
	T = T - 1
