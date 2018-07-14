public class Multiplicacao_de_Cadeia_de_Matrizes {
	static String string_gulosa = "";

	public static void main(String[] args) {
		String arqEntrada = "entrada.txt";
		String entrada = Arquivo.Read(arqEntrada);

		if (entrada.isEmpty()) {
			System.out.println("Erro ao ler do arquivo!");
		} else {
			String saida_dinamica = "", saida_gulosa = "";
			int qtd_instancias = Integer.parseInt(entrada.split("\n")[0]);
			int[][] p = new int[qtd_instancias][];
			for (int i = 1; i <= qtd_instancias; i++) {
				String linha = entrada.split("\n")[i];
				int tam_cadeia = Integer.parseInt(linha.split(" ")[0]);
				p[i - 1] = new int[tam_cadeia + 1];
				for (int j = 1; j <= tam_cadeia + 1; j++) {
					p[i - 1][j - 1] = Integer.parseInt(linha.split(" ")[j]);
				}
				saida_dinamica += multiplicacaoDinamico(p[i - 1]) + "\r\n";
				int mult_guloso = multiplicacaoGuloso(p[i - 1], 1, tam_cadeia);
				string_gulosa += " " + mult_guloso;
				saida_gulosa += string_gulosa + "\r\n";
				string_gulosa = "";
			}
			if (!Arquivo.Write("saidaDinamica.txt", saida_dinamica)) {
				System.out.println("Erro ao salvar o arquivo saidaDinamica!");
			}
			if (!Arquivo.Write("saidaGulosa.txt", saida_gulosa)) {
				System.out.println("Erro ao salvar o arquivo saidaGa!");
			}
		}
	}

	static String multiplicacaoDinamico(int[] p) {
		int n = p.length - 1;
		int[][] m = new int[p.length][p.length];
		int[][] s = new int[n][p.length];
		for (int l = 2; l <= n; l++) {
			for (int i = 1; i <= n - l + 1; i++) {
				int j = i + l - 1;
				m[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					int q = m[i][k] + m[k + 1][j] + (p[i - 1] * p[k] * p[j]);
					if (q < m[i][j]) {
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
		return impressaoDinamico(s, 1, n) + " " + m[1][n];
	}

	static String impressaoDinamico(int[][] s, int i, int j) {
		if (i == j) {
			return "A" + i;
		} else {
			return "(" + impressaoDinamico(s, i, s[i][j])
					+ impressaoDinamico(s, s[i][j] + 1, j) + ")";
		}
	}

	static int multiplicacaoGuloso(int[] p, int i, int j) {
		if (i == j) {
			string_gulosa += "A" + i;
			return 0;
		}
		int x = i, menor, menor_final = Integer.MAX_VALUE;
		for (int k = i; k < j; k++) {
			menor = p[i - 1] * p[k] * p[j];
			if (menor < menor_final) {
				menor_final = menor;
				x = k;
			}
		}
		string_gulosa += "(";
		int total = multiplicacaoGuloso(p, i, x)
				+ multiplicacaoGuloso(p, x + 1, j) + menor_final;
		string_gulosa += ")";
		return total;
	}

}
