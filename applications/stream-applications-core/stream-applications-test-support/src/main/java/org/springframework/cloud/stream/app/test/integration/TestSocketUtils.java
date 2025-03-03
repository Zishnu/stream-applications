/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.app.test.integration;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.net.ServerSocketFactory;

import org.springframework.util.Assert;

/**
 * Simple utility methods for working with network sockets &mdash; for example,
 * for finding available ports on {@code localhost}.
 *
 * <p>Within this class, a TCP port refers to a port for a {@link ServerSocket};
 * whereas, a UDP port refers to a port for a {@link DatagramSocket}.
 *
 * <p>{@code SocketUtils} was introduced in Spring Framework 4.0, primarily to
 * assist in writing integration tests which start an external server on an
 * available random port. However, these utilities make no guarantee about the
 * subsequent availability of a given port and are therefore unreliable. Instead
 * of using {@code SocketUtils} to find an available local port for a server, it
 * is recommended that you rely on a server's ability to start on a random port
 * that it selects or is assigned by the operating system. To interact with that
 * server, you should query the server for the port it is currently using.
 *
 * @author Sam Brannen
 * @author Ben Hale
 * @author Arjen Poutsma
 * @author Gunnar Hillert
 * @author Gary Russell
 * @since 4.0
 * @deprecated as of Spring Framework 5.3.16, to be removed in 6.0; see
 */
@Deprecated
public final class TestSocketUtils {

	/**
	 * The default minimum value for port ranges used when finding an available
	 * socket port.
	 */
	public static final int PORT_RANGE_MIN = 1024;

	/**
	 * The default maximum value for port ranges used when finding an available
	 * socket port.
	 */
	public static final int PORT_RANGE_MAX = 65535;

	private static final Random random = new Random(System.nanoTime());

	private TestSocketUtils() {
	}

	/**
	 * Find an available TCP port randomly selected from the range
	 * [{@value #PORT_RANGE_MIN}, {@value #PORT_RANGE_MAX}].
	 * @return an available TCP port number
	 * @throws IllegalStateException if no available port could be found
	 */
	public static int findAvailableTcpPort() {
		return findAvailableTcpPort(PORT_RANGE_MIN);
	}

	/**
	 * Find an available TCP port randomly selected from the range
	 * [{@code minPort}, {@value #PORT_RANGE_MAX}].
	 * @param minPort the minimum port number
	 * @return an available TCP port number
	 * @throws IllegalStateException if no available port could be found
	 */
	public static int findAvailableTcpPort(int minPort) {
		return findAvailableTcpPort(minPort, PORT_RANGE_MAX);
	}

	/**
	 * Find an available TCP port randomly selected from the range
	 * [{@code minPort}, {@code maxPort}].
	 * @param minPort the minimum port number
	 * @param maxPort the maximum port number
	 * @return an available TCP port number
	 * @throws IllegalStateException if no available port could be found
	 */
	public static int findAvailableTcpPort(int minPort, int maxPort) {
		return SocketType.TCP.findAvailablePort(minPort, maxPort);
	}

	/**
	 * Find the requested number of available TCP ports, each randomly selected
	 * from the range [{@value #PORT_RANGE_MIN}, {@value #PORT_RANGE_MAX}].
	 * @param numRequested the number of available ports to find
	 * @return a sorted set of available TCP port numbers
	 * @throws IllegalStateException if the requested number of available ports could not be found
	 */
	public static SortedSet<Integer> findAvailableTcpPorts(int numRequested) {
		return findAvailableTcpPorts(numRequested, PORT_RANGE_MIN, PORT_RANGE_MAX);
	}

	/**
	 * Find the requested number of available TCP ports, each randomly selected
	 * from the range [{@code minPort}, {@code maxPort}].
	 * @param numRequested the number of available ports to find
	 * @param minPort the minimum port number
	 * @param maxPort the maximum port number
	 * @return a sorted set of available TCP port numbers
	 * @throws IllegalStateException if the requested number of available ports could not be found
	 */
	public static SortedSet<Integer> findAvailableTcpPorts(int numRequested, int minPort, int maxPort) {
		return SocketType.TCP.findAvailablePorts(numRequested, minPort, maxPort);
	}

	/**
	 * Find an available UDP port randomly selected from the range
	 * [{@value #PORT_RANGE_MIN}, {@value #PORT_RANGE_MAX}].
	 * @return an available UDP port number
	 * @throws IllegalStateException if no available port could be found
	 */
	public static int findAvailableUdpPort() {
		return findAvailableUdpPort(PORT_RANGE_MIN);
	}

	/**
	 * Find an available UDP port randomly selected from the range
	 * [{@code minPort}, {@value #PORT_RANGE_MAX}].
	 * @param minPort the minimum port number
	 * @return an available UDP port number
	 * @throws IllegalStateException if no available port could be found
	 */
	public static int findAvailableUdpPort(int minPort) {
		return findAvailableUdpPort(minPort, PORT_RANGE_MAX);
	}

	/**
	 * Find an available UDP port randomly selected from the range
	 * [{@code minPort}, {@code maxPort}].
	 * @param minPort the minimum port number
	 * @param maxPort the maximum port number
	 * @return an available UDP port number
	 * @throws IllegalStateException if no available port could be found
	 */
	public static int findAvailableUdpPort(int minPort, int maxPort) {
		return SocketType.UDP.findAvailablePort(minPort, maxPort);
	}

	/**
	 * Find the requested number of available UDP ports, each randomly selected
	 * from the range [{@value #PORT_RANGE_MIN}, {@value #PORT_RANGE_MAX}].
	 * @param numRequested the number of available ports to find
	 * @return a sorted set of available UDP port numbers
	 * @throws IllegalStateException if the requested number of available ports could not be found
	 */
	public static SortedSet<Integer> findAvailableUdpPorts(int numRequested) {
		return findAvailableUdpPorts(numRequested, PORT_RANGE_MIN, PORT_RANGE_MAX);
	}

	/**
	 * Find the requested number of available UDP ports, each randomly selected
	 * from the range [{@code minPort}, {@code maxPort}].
	 * @param numRequested the number of available ports to find
	 * @param minPort the minimum port number
	 * @param maxPort the maximum port number
	 * @return a sorted set of available UDP port numbers
	 * @throws IllegalStateException if the requested number of available ports could not be found
	 */
	public static SortedSet<Integer> findAvailableUdpPorts(int numRequested, int minPort, int maxPort) {
		return SocketType.UDP.findAvailablePorts(numRequested, minPort, maxPort);
	}


	private enum SocketType {

		TCP {
			@Override
			protected boolean isPortAvailable(int port) {
				try {
					ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(
							port, 1, InetAddress.getByName("localhost"));
					serverSocket.close();
					return true;
				}
				catch (Exception ex) {
					return false;
				}
			}
		},

		UDP {
			@Override
			protected boolean isPortAvailable(int port) {
				try {
					DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
					socket.close();
					return true;
				}
				catch (Exception ex) {
					return false;
				}
			}
		};

		/**
		 * Determine if the specified port for this {@code SocketType} is
		 * currently available on {@code localhost}.
		 */
		protected abstract boolean isPortAvailable(int port);

		/**
		 * Find a pseudo-random port number within the range
		 * [{@code minPort}, {@code maxPort}].
		 * @param minPort the minimum port number
		 * @param maxPort the maximum port number
		 * @return a random port number within the specified range
		 */
		private int findRandomPort(int minPort, int maxPort) {
			int portRange = maxPort - minPort;
			return minPort + random.nextInt(portRange + 1);
		}

		/**
		 * Find an available port for this {@code SocketType}, randomly selected
		 * from the range [{@code minPort}, {@code maxPort}].
		 * @param minPort the minimum port number
		 * @param maxPort the maximum port number
		 * @return an available port number for this socket type
		 * @throws IllegalStateException if no available port could be found
		 */
		int findAvailablePort(int minPort, int maxPort) {
			Assert.isTrue(minPort > 0, "'minPort' must be greater than 0");
			Assert.isTrue(maxPort >= minPort, "'maxPort' must be greater than or equal to 'minPort'");
			Assert.isTrue(maxPort <= PORT_RANGE_MAX, "'maxPort' must be less than or equal to " + PORT_RANGE_MAX);

			int portRange = maxPort - minPort;
			int candidatePort;
			int searchCounter = 0;
			do {
				if (searchCounter > portRange) {
					throw new IllegalStateException(String.format(
							"Could not find an available %s port in the range [%d, %d] after %d attempts",
							name(), minPort, maxPort, searchCounter));
				}
				candidatePort = findRandomPort(minPort, maxPort);
				searchCounter++;
			}
			while (!isPortAvailable(candidatePort));

			return candidatePort;
		}

		/**
		 * Find the requested number of available ports for this {@code SocketType},
		 * each randomly selected from the range [{@code minPort}, {@code maxPort}].
		 * @param numRequested the number of available ports to find
		 * @param minPort the minimum port number
		 * @param maxPort the maximum port number
		 * @return a sorted set of available port numbers for this socket type
		 * @throws IllegalStateException if the requested number of available ports could not be found
		 */
		SortedSet<Integer> findAvailablePorts(int numRequested, int minPort, int maxPort) {
			Assert.isTrue(minPort > 0, "'minPort' must be greater than 0");
			Assert.isTrue(maxPort > minPort, "'maxPort' must be greater than 'minPort'");
			Assert.isTrue(maxPort <= PORT_RANGE_MAX, "'maxPort' must be less than or equal to " + PORT_RANGE_MAX);
			Assert.isTrue(numRequested > 0, "'numRequested' must be greater than 0");
			Assert.isTrue((maxPort - minPort) >= numRequested,
					"'numRequested' must not be greater than 'maxPort' - 'minPort'");

			SortedSet<Integer> availablePorts = new TreeSet<>();
			int attemptCount = 0;
			while ((++attemptCount <= numRequested + 100) && availablePorts.size() < numRequested) {
				availablePorts.add(findAvailablePort(minPort, maxPort));
			}

			if (availablePorts.size() != numRequested) {
				throw new IllegalStateException(String.format(
						"Could not find %d available %s ports in the range [%d, %d]",
						numRequested, name(), minPort, maxPort));
			}

			return availablePorts;
		}
	}

}
