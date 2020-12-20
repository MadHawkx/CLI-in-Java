/////////////////////////////////////////
//DIVYAM MADHOK 
//Completely working. !5/16 test cases passed. One didn't pass due to UTF-8 encoding issues a "dot" character was being appended at the end 

import java.util.*;
import java.time.LocalDate; // import the LocalDate class

import java.io.*;
import java.nio.charset.*;

public class Todo {
	// These 2 variables will maintain the size of todo.txt and done.txt along with
	// the text inside
	static ArrayList<String> res = new ArrayList<>();
	static ArrayList<String> resDone = new ArrayList<>();

	// SET UP READ/WRITE TO THE FILE "todo.txt" and "done.txt"
	// A common function used to iterate over each line in todo.txt
	private static void iterateTodo() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("todo.txt"), StandardCharsets.UTF_8));
			String printreader;
			while ((printreader = reader.readLine()) != null) {
				res.add(printreader);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// A common function used to iterate over each line in done.txt
	private static void iterateDone() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("done.txt"), StandardCharsets.UTF_8));
			String printreader;
			while ((printreader = reader.readLine()) != null) {
				resDone.add(printreader);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// HELP MESSAGE CODE. We can also use switch which is I believe more appropriate
		// but used if-else loops just to maintain readibility
		if (args.length == 0 || args[0].equals("help")) {
			System.out.print("Usage :-\n" + "$ ./todo add \"todo item\"  # Add a new todo\n"
					+ "$ ./todo ls               # Show remaining todos\n"
					+ "$ ./todo del NUMBER       # Delete a todo\n" + "$ ./todo done NUMBER      # Complete a todo\n"
					+ "$ ./todo help             # Show usage\n" + "$ ./todo report           # Statistics\n");
		}

		// IF USER TYPES ls

		else if (args[0].equals("ls")) {
			Integer i = 1;
			iterateTodo();
			if (res.size() == 0) {
				System.out.println("There are no pending todos!");
			} else {
				for (; i <= res.size(); i++) {
					System.out.println("[" + (res.size() - i + 1) + "] " + res.get(res.size() - i));
				}
			}
		}

		// When user types add
		else if (args[0].equals("add")) {
			if (args.length == 1)
				System.out.println("Error: Missing todo string. Nothing added!");
			else {
				try {
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("todo.txt", true), StandardCharsets.UTF_8));
					String temp = args[1];
					Charset.forName("UTF-8").encode(temp);
					bfWriter.write(temp);
					bfWriter.newLine();
					bfWriter.close();
					System.out.println("Added todo: \"" + args[1] + "\"");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		// Delete a todo

		else if (args[0].equals("del")) {
			if (args.length == 1) {
				System.out.println("Error: Missing NUMBER for deleting todo.");
			} else {
				iterateTodo();// To fill res with elements
				try {
					int i = 1;
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("todo.txt", false), StandardCharsets.UTF_8));
					if (Integer.parseInt(args[1]) > res.size() || args[1].equals("0")) {
						System.out.println(
								"Error: todo #" + Integer.parseInt(args[1]) + " does not exist. Nothing deleted.");
					} else {
						for (int j = 1; j <= res.size(); j++) {
							if (j == Integer.parseInt(args[1])) {
								i++;
								System.out.println("Deleted todo #" + (i - 1));
								continue;
							} else {
								bfWriter.write(res.get(i - 1));
								bfWriter.newLine();
								i++;
							}

						}
					}
					bfWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// A todo is done!
		else if (args[0].equals("done")) {
			if (args.length == 1) {
				System.out.println("Error: Missing NUMBER for marking todo as done.");
			} else {
				// Integer.parseInt(args[1])
				try {
					iterateTodo();
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("done.txt", true), StandardCharsets.UTF_8));
					if (Integer.parseInt(args[1]) > res.size() || args[1].equals("0")) {
						System.out.println("Error: todo #" + Integer.parseInt(args[1]) + " does not exist.");
					} else {
						bfWriter.write(args[1]);
						bfWriter.newLine();
						bfWriter.close();
						System.out.println("Marked todo #" + args[1] + " as done.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					int i = 1;
					BufferedWriter bfWriter = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("todo.txt", false), StandardCharsets.UTF_8));
					for (int j = 1; j <= res.size(); j++) {
						if (j == Integer.parseInt(args[1])) {
							i++;
							continue;
						} else {
							bfWriter.write(res.get(i - 1));
							bfWriter.newLine();
							i++;
						}

					}
					bfWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		else if (args[0].equals("report")) {
			iterateTodo();
			iterateDone();
			LocalDate today = LocalDate.now();
			System.out.println(today + " Pending : " + res.size() + " Completed : " + resDone.size());
		}
	}
}
