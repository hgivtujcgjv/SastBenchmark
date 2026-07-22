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

PROFILE_LOCK = None
QUOTA_LOCK = None


def init(app):
    import threading
    import time

    global PROFILE_LOCK
    global QUOTA_LOCK

    PROFILE_LOCK = threading.Lock()
    QUOTA_LOCK = threading.Lock()

    @app.route('/benchmark/racecond-00/BenchmarkTest01233', methods=['GET', 'POST'])
    def BenchmarkTest01233():

        def update_profile():
            with PROFILE_LOCK:
                time.sleep(0.02)
                with QUOTA_LOCK:
                    return "profile"

        def update_quota():
            with QUOTA_LOCK:
                time.sleep(0.02)
                with PROFILE_LOCK:
                    return "quota"

        # which can deadlock under concurrent execution.
        first = threading.Thread(target=update_profile)
        second = threading.Thread(target=update_quota)
        first.start()
        second.start()
        first.join(timeout=0.05)
        second.join(timeout=0.05)

        return jsonify(profile_done=not first.is_alive(), quota_done=not second.is_alive())

