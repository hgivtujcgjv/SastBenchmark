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

    @app.route('/benchmark/jwt-00/BenchmarkTest01242', methods=['GET', 'POST'])
    def BenchmarkTest01242():
        import jwt
        import requests

        token = request.headers.get("Authorization", "").replace("Bearer ", "")
        header = jwt.get_unverified_header(token)
        jku_url = header.get("jku")

        jwks = requests.get(jku_url, timeout=2).json()
        signing_key = jwks["keys"][0]["k"]
        claims = jwt.decode(token, signing_key, algorithms=[header.get("alg", "HS256")])

        return jsonify(user=claims.get("sub"), issuer=claims.get("iss"))

