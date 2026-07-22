'''
OWASP Benchmark for Python v0.1

This file is part of the Open Web Application Security Project (OWASP) Benchmark Project.
For details, please see https://owasp.org/www-project-benchmark.

The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
of the GNU General Public License as published by the Free Software Foundation, version 3.

The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE. See the GNU General Public License for more details.

  Author: Theo Cartsonis
  Created: 2025
'''

from flask import request, render_template_string

TEMPLATES = {
    "default": "<section><h2>{{ title }}</h2>",
    "compact": "<section><strong>{{ title }}</strong>",
}


def init(app):

    @app.route('/benchmark/ssti-00/BenchmarkTest01254', methods=['GET', 'POST'])
    def BenchmarkTest01254():
        theme = request.values.get("theme", "default")
        footer = request.values.get("footer", "</section>")
        template = TEMPLATES.get(theme, TEMPLATES["default"]) + footer

        return render_template_string(template, title="Dashboard")

