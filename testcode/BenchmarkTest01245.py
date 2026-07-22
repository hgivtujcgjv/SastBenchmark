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

JWT_SECRET = "benchmark-secret"


def init(app):

    @app.route('/benchmark/jwt-00/BenchmarkTest01245', methods=['GET', 'POST'])
    def BenchmarkTest01245():
        import jwt

        token = request.headers.get("Authorization", "").replace("Bearer ", "")

        claims = jwt.decode(
            token,
            JWT_SECRET,
            algorithms=["HS256"],
            options={"verify_exp": False, "verify_aud": False, "verify_iss": False},
        )

        return jsonify(user=claims.get("sub"), scope=claims.get("scope", ""))

