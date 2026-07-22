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

from flask import request, redirect


def init(app):

    @app.route('/benchmark/redirect-00/BenchmarkTest01237', methods=['GET', 'POST'])
    def BenchmarkTest01237():
        target = request.form.get("return_to") or request.args.get("return_to") or "/"
        decoded_target = target.strip()

        return redirect(decoded_target)

