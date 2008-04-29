import java.net.URLClassLoader;
import java.net.URL;
import java.io.*;

public class ReloadingClassLoader extends ClassLoader {
		public ReloadingClassLoader(File[] directories) {
			this.directories = directories;
		}

		public Class<?> loadClass(String name) throws ClassNotFoundException {
			byte[] classBytes = null;
			try {
							classBytes = getBytes(name.replace(".", "/") + ".class");
						} catch (IOException e) {
							e.printStackTrace();
							return findSystemClass(name);
						}

						return defineClass(name, classBytes, 0, classBytes.length);
					}

					private byte[] getBytes(String filename) throws IOException {

						File file = new File(filename);
						long len = file.length();
						byte raw[] = new byte[(int) len];
						FileInputStream fin = new FileInputStream(file);
						int r = fin.read(raw);
						if (r != len)
							throw new IOException("Can't read all, " + r + " != " + len);
						fin.close();
						return raw;
					}
}