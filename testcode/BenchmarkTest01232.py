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

from flask import request, make_response


def init(app):

    @app.route('/benchmark/racecond-00/BenchmarkTest01232', methods=['GET', 'POST'])
    def BenchmarkTest01232():
        import os
        import tempfile
        import time

        token = request.values.get("token", "guest")
        token_path = os.path.join(tempfile.gettempdir(), f"benchmark-token-{token}.txt")
        # the file between os.path.exists() and open().
        if not os.path.exists(token_path):
            time.sleep(0.02)
            with open(token_path, "w", encoding="utf-8") as token_file:
                token_file.write(request.values.get("value", "pending"))

        return make_response(f"token file: {token_path}")

