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

ACCOUNTS = {"alice": 1000}


def init(app):

    @app.route('/benchmark/racecond-00/BenchmarkTest01231', methods=['GET', 'POST'])
    def BenchmarkTest01231():
        import time

        amount = int(request.values.get("amount", "25"))
        current_balance = ACCOUNTS["alice"]

        if current_balance >= amount:
            time.sleep(0.02)
            ACCOUNTS["alice"] = current_balance - amount

        return jsonify(account="alice", balance=ACCOUNTS["alice"])

