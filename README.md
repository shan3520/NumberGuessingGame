# Algorithmic Number Guessing & Search Engine

[![Java 21+](https://img.shields.io/badge/Java-21+-orange.svg)](https://www.oracle.com/java/)
[![Algorithmic Optimality](https://img.shields.io/badge/Search%20Complexity-O(log%20N)%20Provably%20Optimal-brightgreen.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

An information-theoretic search engine and interactive game exploring minimax decision trees and Shannon entropy reduction in discrete search spaces. Implemented in modern Java with structured modular architecture and rigorous verification suites.

---

## Information-Theoretic Foundations

The classic number guessing game is fundamentally an optimal binary decision search problem over a discrete uniform distribution $U\{1, N\}$.

### 1. Entropy & Information Gain
The initial uncertainty of the target number $X \in \{1, \dots, N\}$ is measured by Shannon Entropy:
$$H(X) = -\sum_{i=1}^N \frac{1}{N} \log_2\left(\frac{1}{N}\right) = \log_2(N) \text{ bits}$$

Each comparison query $q$ against an oracle yields ternary feedback:
$$\text{Feedback}(q) \in \{\text{TOO\_LOW}, \text{CORRECT}, \text{TOO\_HIGH}\}$$

Selecting the median candidate $q^* = \text{low} + \lfloor \frac{\text{high} - \text{low}}{2} \rfloor$ partitions the remaining search space into symmetric halves, maximizing expected information gain:
$$\mathbb{E}[\Delta H] \approx 1.0 \text{ bit per query}$$

### 2. Worst-Case Search Bound
A binary comparison decision tree of depth $k$ can distinguish at most $2^k - 1$ internal states before terminating. Thus, for any search range of size $N$, the minimax optimal guess count is provably bounded by:
$$k_{\text{max}} = \lfloor \log_2(N) \rfloor + 1$$

| Search Range ($N$) | Theoretical Max Queries | Empirical AI Results |
|---|---|---|
| $1 – 100$ | 7 queries | 100% solved in $\le 7$ |
| $1 – 1,024$ | 11 queries | 100% solved in $\le 11$ |
| $1 – 1,000,000$ | 20 queries | 100% solved in $\le 20$ |

---

## Project Structure

```
NumberGuessingGame/
├── src/com/engine/
│   ├── Feedback.java              # Ternary feedback enum (TOO_LOW, CORRECT, TOO_HIGH)
│   ├── GameResult.java            # Immutable session record with optimality metrics
│   ├── BinarySearchGuesser.java   # Minimax entropy halving AI solver
│   ├── GameEngine.java            # Oracle & state manager
│   └── Main.java                  # Interactive CLI entrypoint (Human / AI Demo)
├── tests/com/engine/
│   └── TestBinarySearchGuesser.java # Exhaustive mathematical verification suite
└── LICENSE                        # MIT License
```

---

## Build & Execution

### Compilation
```bash
javac -d bin src/com/engine/*.java tests/com/engine/*.java
```

### Running Algorithmic Verification Tests
Runs over 1,100 simulated trials across small, power-of-two, and million-element intervals:
```bash
java -cp bin com.engine.TestBinarySearchGuesser
```

### Interactive CLI Execution
```bash
java -cp bin com.engine.Main
```

---

## License

MIT License. See [LICENSE](LICENSE) for details.
