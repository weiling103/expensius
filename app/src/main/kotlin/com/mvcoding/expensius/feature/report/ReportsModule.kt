/*
 * Copyright (C) 2016 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.expensius.feature.report

import com.memoizrlabs.Shank.registerFactory
import com.memoizrlabs.ShankModule
import com.mvcoding.expensius.feature.Filter
import com.mvcoding.expensius.feature.transaction.TransactionType
import com.mvcoding.expensius.feature.transaction.provideTransactionsProvider
import com.mvcoding.expensius.provideRxSchedulers
import com.mvcoding.expensius.provideSettings
import org.joda.time.DateTime
import org.joda.time.Interval

class ReportsModule : ShankModule {
    override fun registerFactories() {
        tagsReportPresenter()
    }

    private fun tagsReportPresenter() = registerFactory(TagsReportPresenter::class.java, { transactionType: TransactionType ->
        val startOfTomorrow = DateTime.now().plusDays(1).withTimeAtStartOfDay()
        val last30Days = Interval(startOfTomorrow.minusDays(30), startOfTomorrow)
        val filter = Filter()
        filter.setInterval(last30Days)
        filter.setTransactionType(transactionType)
        TagsReportPresenter(filter, provideTransactionsProvider(), provideSettings(), provideRxSchedulers())
    })
}