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

INVENTORY = {"sku-100": 1}


def init(app):

    @app.route('/benchmark/racecond-00/BenchmarkTest01234', methods=['GET', 'POST'])
    def BenchmarkTest01234():
        import time

        sku = request.values.get("sku", "sku-100")
        requested = int(request.values.get("count", "1"))
        stock = INVENTORY.get(sku, 0)

        if stock >= requested:
            time.sleep(0.02)
            INVENTORY[sku] = stock - requested
            status = "reserved"
        else:
            status = "sold_out"

        return jsonify(sku=sku, status=status, remaining=INVENTORY.get(sku, 0))

