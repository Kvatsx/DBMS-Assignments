import matplotlib.pyplot as plt
import numpy as np

def main():
    # Time: 5 Seconds
    all_thread_pool_vals = [10, 20, 30, 40, 50]

    serial_values = [804, 829, 789, 795, 813]
    twopl_values = [7043, 13027, 18978, 24539, 28842]

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

    graph_x_axis = ["Reserve", "Cancel", "My Flights", "Total Reservations", "Transfer", "Transaction Mix"]
    serial_count = [827, 820, 801, 796, 818, 762]
    twopl_count = [13554, 13318, 13394, 13148, 13030, 12362]

    plt.bar(graph_x_axis, serial_count)
    plt.ylim(ymin=0, ymax=1200)
    plt.title("Serial Locking")
    plt.figtext(0.7, 0.8, "Threads: 20")
    plt.figtext(0.7, 0.84, "Time: 5s")
    plt.show()

    plt.bar(graph_x_axis, twopl_count)
    plt.ylim(ymin=0, ymax=15000)
    plt.title("Two Phase Locking")
    plt.figtext(0.7, 0.8, "Threads: 20")
    plt.figtext(0.7, 0.84, "Time: 5s")
    plt.show()

if __name__ == '__main__':
    main()