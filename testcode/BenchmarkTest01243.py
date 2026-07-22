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

KEY_DIRECTORY = "/tmp/benchmark-jwt-keys"


def init(app):

    @app.route('/benchmark/jwt-00/BenchmarkTest01243', methods=['GET', 'POST'])
    def BenchmarkTest01243():
        import jwt
        import os

        token = request.headers.get("Authorization", "").replace("Bearer ", "")
        header = jwt.get_unverified_header(token)
        key_id = header.get("kid", "default.key")
        key_path = os.path.join(KEY_DIRECTORY, key_id)

        with open(key_path, "r", encoding="utf-8") as key_file:
            verification_key = key_file.read()

        claims = jwt.decode(token, verification_key, algorithms=["HS256", "RS256"])

        return jsonify(user=claims.get("sub"), key=key_id)

