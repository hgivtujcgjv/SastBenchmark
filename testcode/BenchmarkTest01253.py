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


def init(app):

    @app.route('/benchmark/ssti-00/BenchmarkTest01253', methods=['GET', 'POST'])
    def BenchmarkTest01253():
        base_template = "<main><h2>Report</h2>{body}</main>"
        body = request.values.get("body", "<p>{{ status }}</p>")
        template = base_template.replace("{body}", body)

        return render_template_string(template, status="ready")

