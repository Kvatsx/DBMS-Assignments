# Kaustav Vats (2016048)
# Yashit Maheshwary (2016123)
'''
Program 2 that uses a metadata file and the script reads the data from the data file before the main program execution.
'''

MetaDataFormat = []
# with open("metadata_2016048_2016123.txt", "r") as file:
with open("metadata_2016048_2016123.txt", "r") as file:
    for line in file:
        CurrentLine = line
        MetaDataFormat.append(CurrentLine.rstrip('\n').split(', '))

name = str(input("Enter Name of your Data File: "))
Data = []
with open(name, "r") as file:
    for line in file.readlines():
        Data.append(line.rstrip('\n').split(', '))

filtered_data = []
temp = []
for item in range(1, len(MetaDataFormat)):
    temp.append(MetaDataFormat[item][0])

filtered_data.append(temp)

column_order = {}
for counter in range(1, len(MetaDataFormat)):
    current_key = MetaDataFormat[counter][0]
    for item_counter in range(0, len(Data[0])):
        if Data[0][item_counter] == current_key:
            column_order[counter] = item_counter

for i in range(1,len(Data)):
    temp = []
    for j in column_order:
        temp.append(Data[i][column_order[j]])
    filtered_data.append(temp)

for i in range(1, len(filtered_data)):
    for j in range(len(filtered_data[i])):
        if len(filtered_data[i][j]) > int(MetaDataFormat[j + 1][2]):
            print("Size Exceeding Limit in %s. Redacting Output."%(filtered_data[i][j]))
            filtered_data[i][j] = filtered_data[i][j][:int(
                MetaDataFormat[j + 1][2])]

while True:
    print("1) Enter '1' to Print content of data file.")
    print(
        "2) Enter '2' to Give a fieldname as an argument to print sum of values of the given field."
    )
    print("3) Enter '3' to Write data to file.")
    print("4) Enter '4' to Exit.")
    key = int(input("Enter your choice: "))

    if key == 1:
        for arr in filtered_data:
            for i in range(0, len(arr)):
                print(arr[i], end=', ')
            print()
    elif key == 2:
        print("OPTIONS:")
        for counter in range(1, len(MetaDataFormat)):
            print(str(counter) + ". " + MetaDataFormat[counter][0])
        selected_option = int(input("Enter Column Number: "))
        try:
            field = MetaDataFormat[selected_option][0]
            for brr in MetaDataFormat:
                if (brr[0].lower() == field.lower()):
                    if (brr[1] != 'Character' and brr[1] != 'Boolean'):
                        position = -1
                        for i in range(0, len(filtered_data[0])):
                            if (filtered_data[0][i].lower() == field.lower()):
                                position = i
                        Sum = 0
                        for i in range(1, len(filtered_data)):
                            if brr[1] == 'Float' or brr[1] == "Long":
                                Sum = Sum + float(filtered_data[i][position])
                            elif brr[1] == 'Integer':
                                Sum = Sum + int(filtered_data[i][position])
                        print("Sum:", field, "->", Sum)
                    else:
                        print("Non-Numeric field, Sum computation not possible.")
                    break
        except:
            print("Column Doesn't Exist")
    elif key == 3:
        # Write to user data file.
        FileName = str(input("Enter Name of a file: "))
        file = open(FileName, "w")
        for arr in filtered_data:
            a = ""
            for i in range(0, len(arr)):
                a = a + arr[i] + ", "
            file.write(a + "\n")
        file.close()
        print("Write Completed!")
    elif key == 4:
        break
    print()
