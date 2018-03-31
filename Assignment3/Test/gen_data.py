"""
 *  Author : YashitM 
 *  Created On : Sat Mar 31 2018
 *  File : gen_data.py
"""
import csv
import random
import string

def get_random_string(len):
    rand = random.randint(1,len)
    return ''.join(random.choice(string.ascii_letters) for _ in range(rand))


def get_random_num(len):
    rand = random.randint(1, len)
    return random.randint(10**rand,10**(rand+1)-1)

def gen_data():
    data = list()
    data.append([
        'Validity Tag', 'Instructor ID', 'Instructor Name', 'Department',
        'Salary'
    ])
    for _ in range(10):
        data_row = list()
        validity_tag = get_random_string(4)
        instructor_id = get_random_string(4)
        instructor_name = get_random_string(20)
        department = get_random_string(10)
        salary = get_random_num(10)

        data_row.append(validity_tag)
        data_row.append(instructor_id)
        data_row.append(instructor_name)
        data_row.append(department)
        data_row.append(salary)

        print(validity_tag, instructor_id, instructor_name, department, salary)

        data.append(data_row)
    return data

def main():
    data = gen_data()
    with open("datafile.csv", 'w') as myfile:
        wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)
        for row in data:   
            wr.writerow(row)

if __name__ == '__main__':
    main()