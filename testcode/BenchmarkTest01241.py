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

from flask import request, jsonify


def init(app):

    @app.route('/benchmark/jwt-00/BenchmarkTest01241', methods=['GET', 'POST'])
    def BenchmarkTest01241():
        import jwt

        token = request.headers.get("Authorization", "").replace("Bearer ", "")

        claims = jwt.decode(token, options={"verify_signature": False}, algorithms=["none"])

        return jsonify(user=claims.get("sub"), admin=claims.get("admin", False))

