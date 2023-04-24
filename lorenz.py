import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint

a = 10.0  # Prandtl
b = 28.0  # Rayleigh
c = 8.0/3.0  # Beta


def lorenz(variaveis, t):
    x, y, z = variaveis
    dx_dt = a*(y - x)      # X' = a.(Y – X)
    dy_dt = x*(b-z) - y    # Y' = X*(b-z) - z
    dz_dt = x*y - c*z      # Z' = X.Y – c.Z
    return [dx_dt, dy_dt, dz_dt]


def show_figure(resultado):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    ax.plot(resultado[:, 0], resultado[:, 1], resultado[:, 2])
    ax.legend()
    plt.show()


# condições iniciais
x0 = 1.5  # Taxa de convecção
y0 = 10   # Correntes ascendentes e descendente
z0 = 15   # Correntes ar quente e frio
estado0 = [x0, y0, z0]

# intervalo de tempo
t = np.arange(0, 40.0, 0.01)

# resolver as equações de Lorenz
result = odeint(lorenz, estado0, t)

# plotando os resultados em um espaço de fase
show_figure(result)
