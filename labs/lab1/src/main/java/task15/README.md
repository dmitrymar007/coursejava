EnrollmentEngine получает одно правило EnrollmentRule, проверяет заявку и обновляет списки зачисленных и ожидания. Каждое правило возвращает RuleResult: Pass, Fail или WaitList. Составные правила создаются через EnrollmentRule.allOf и anyOf.

Диаграмма зависимостей:

- Main -> EnrollmentEngine
- EnrollmentEngine -> EnrollmentRule (интерфейс)
- EnrollmentEngine -> RuleResult (sealed: Pass, Fail, WaitList)
- EnrollmentRule -> EnrollmentRequest, RuleResult

Реализации EnrollmentRule:
- AgeRule
- PrerequisiteRule
- CapacityRule
- PaymentRule
- ScholarshipRule
- NoDuplicateRule
- CompositeRule (абстрактный класс), от него AllOfRule и AnyOfRule
- лямбды, например notBlacklisted в Main

Движок зависит только от интерфейса EnrollmentRule и от RuleResult и конкретных правил не знает.

Dynamic dispatch: в Main правила лежат в списке типа EnrollmentRule, и цикл вызывает rule.evaluate. Код вызова один, а выполняется реализация конкретного класса. Композиты тоже вызывают вложенные правила через интерфейс.

Где уместно наследование: RuleResult (закрытый набор вариантов результата), реализации интерфейса EnrollmentRule и CompositeRule. У CompositeRule общий алгоритм evaluate (final), а отличается только метод combine. Класс не публичный, снаружи расширять нельзя.

Где выбрана композиция: allOf и anyOf содержат другие правила, а не наследуют от них. Движок тоже содержит правило. Новые политики собираются из готовых правил без новых классов.

Гарантии sealed: набор результатов конечен и известен компилятору. Switch в движке исчерпывающий и без default, поэтому новый вариант результата вызовет ошибку компиляции во всех местах разбора. Снаружи добавить свой вариант нельзя. Сами правила при этом открыты, и новое правило добавляется без изменений в движке.
