import matplotlib.pyplot as plt
import numpy as np

def main():
    # Time: 5 Seconds
    all_thread_pool_vals = [10, 20, 30, 40, 50]

    serial_values = [581, 545, 513, 520, 597]
    twopl_values = [4877, 8248, 10781, 13000, 15000]

    plt.plot(all_thread_pool_vals, serial_values)
    plt.ylim(ymin=0, ymax=2*np.mean(np.array(serial_values)))
    plt.title("Serial")
    plt.ylabel('Transaction Count')
    plt.xlabel('Thread Count')
    plt.show()

    plt.plot(all_thread_pool_vals, twopl_values)
    plt.ylim(ymin=0, ymax=2 * np.mean(np.array(twopl_values)))
    plt.title("Two Phase Locking")
    plt.ylabel('Transaction Count')
    plt.xlabel('Thread Count')
    plt.show()

if __name__ == '__main__':
    main()