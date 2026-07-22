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

from flask import jsonify

COUNTERS = {"invoice": 0}


def init(app):

    @app.route('/benchmark/racecond-00/BenchmarkTest01235', methods=['GET', 'POST'])
    def BenchmarkTest01235():
        import threading
        import time

        request_local_lock = threading.Lock()

        # COUNTERS dictionary from other concurrent requests.
        with request_local_lock:
            current_value = COUNTERS["invoice"]
            time.sleep(0.02)
            COUNTERS["invoice"] = current_value + 1

        return jsonify(invoice_id=COUNTERS["invoice"])

