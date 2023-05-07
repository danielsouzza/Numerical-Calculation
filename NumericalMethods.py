import matplotlib.pyplot as plt
import pandas as pd
import json
import sympy as sp

sx = sp.symbols('x')


class MetodosRefinamentoRaiz:
    def __init__(self, func, tolerance):
        self.fx = func[0]
        self.gx = func[1]
        self.tolerance = tolerance
        
    def bisection(self, interval):
        a, b = map(float, interval)
        f = sp.lambdify(sx, self.fx)
        count = 0
        x = (a+b) / 2
        erro = float("inf")
        while erro > self.tolerance:
            if f(a)*f(x) < 0:
                b = x
            else:
                a = x
            x0 = x
            x = (a + b) / 2
            erro = abs(x - x0)
            count += 1
        return "{:.3f}".format(x), "{:.3f}".format(f(x)), count
    
    def newton(self, x0, maxiter=50):
        f = sp.lambdify(sx, self.fx)
        df = sp.lambdify(sx, sp.diff(self.fx, sx))
        count = 0
        e = float("inf")
        x = x0
        while e > self.tolerance and count < maxiter:
            count += 1
            dx = -f(x0)/df(x0)
            x = x0 + dx
            e = abs(x - x0)
            x0 = x
        return "{:.3f}".format(x), "{:.3f}".format(f(x)), count
    
    def secante(self, points, maxiter=50):
        f = sp.lambdify(sx, self.fx)
        count = 0
        erro = float("inf")
        x1, xn = points
        while erro > self.tolerance and count < maxiter:
            x2 = x1
            x1 = xn
            xn = x1 - ((f(x1)*(x2-x1)) / (f(x2)-f(x1)))
            erro = abs(xn - x1) / abs(xn)
            count += 1
        return "{:.3f}".format(xn), "{:.3f}".format(f(xn)), count
    
    def fix_point(self, x0, maxiter=50):
        f = sp.lambdify(sx, self.fx)
        g = sp.lambdify(sx, self.gx)
        count = 0
        erro = float("inf")
        x = x0
        while erro > self.tolerance and count < maxiter:
            x = g(x0)
            erro = abs(x - x0)
            x0 = x
            count += 1
        
        return "{:.3f}".format(x), "{:.3f}".format(f(x)), count
    
  
def create_table(data, bi, ne, se, fix):
    table = {
        "": ["Bissecção", "Newton", "Secante", "MPF"],
        "Dados iniciais": [data["bisection"], f'x0={data["newton"][0]}', f'x0={data["secante"][0]};x1={data["secante"][1]}', f'x0={data["fixpoint"][0]}'],
        "x̅": [bi[0], ne[0], se[0], fix[0]],
        "f(x̅)": [bi[1], ne[1], se[1], fix[1]],
        "Iteração": [bi[2], ne[2], se[2], fix[2]]
    }
    return table


def show_table(data, title=""):
    fig, ax = plt.subplots(1, 1)
    ax.axis('off')
    ax.axis('tight')
    ax.table(cellText=data.values, colLabels=data.columns, loc='center')
    fig.suptitle(title)
    plt.show()
  

def main():
    with open("dataset.json") as arq:
        data = json.load(arq)
        
    for exercise in data:
        for quest, data in exercise.items():
            function = sp.sympify(data["func"])
            problem = MetodosRefinamentoRaiz(function, float(data["e"]))
            bisect = problem.bisection(data["bisection"])
            newton = problem.newton(data["newton"][0])
            secante = problem.secante(data["secante"])
            fixpoint = problem.fix_point(data["fixpoint"][0])
            data = create_table(data, bisect, newton, secante, fixpoint)
            df = pd.DataFrame(data)
            show_table(df, function[0])
            
        
main()
